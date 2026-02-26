import { useState, useMemo, useEffect } from "react";
import contactAPI from "../services/contactAPI";

function Main() {
  const [contacts, setContacts] = useState([]);
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [phone, setPhone] = useState("");
  const [address, setAddress] = useState({
    streetNumber: "",
    streetName: "",
    postalCode: "",
    state: "",
    country: "",
  });
  const [searchQuery, setSearchQuery] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [isFormOpen, setIsFormOpen] = useState(false);

  // Fetch contacts from backend
  useEffect(() => {
    fetchContacts();
  }, []);

  const fetchContacts = async () => {
    try {
      setLoading(true);
      const response = await contactAPI.getAllContacts();
      setContacts(response.data);
      setError("");
    } catch (err) {
      console.error("Error fetching contacts:", err);
      setError("Unable to connect to server. Make sure the backend is running on http://localhost:8080");
    } finally {
      setLoading(false);
    }
  };

  const filteredContacts = useMemo(() => {
    if (!searchQuery.trim()) return contacts;
    const q = searchQuery.toLowerCase();
    return contacts.filter(
      (c) =>
        (c.firstName && c.firstName.toLowerCase().includes(q)) ||
        (c.lastName && c.lastName.toLowerCase().includes(q)) ||
        (c.phoneNumber && c.phoneNumber.includes(searchQuery)) ||
        (c.addressRequestDto?.streetNumber && c.addressRequestDto.streetNumber.toLowerCase().includes(q)) ||
        (c.addressRequestDto?.streetName && c.addressRequestDto.streetName.toLowerCase().includes(q)) ||
        (c.addressRequestDto?.postalCode && c.addressRequestDto.postalCode.toLowerCase().includes(q)) ||
        (c.addressRequestDto?.state && c.addressRequestDto.state.toLowerCase().includes(q)) ||
        (c.addressRequestDto?.country && c.addressRequestDto.country.toLowerCase().includes(q))
    );
  }, [contacts, searchQuery]);

  const handleAddContact = async (e) => {
    e.preventDefault();
    if (!firstName.trim() || !phone.trim()) {
      setError("First name and phone number are required");
      return;
    }

    try {
      setError("");
      const contactData = {
        firstName: firstName.trim(),
        lastName: lastName.trim(),
        phoneNumber: phone.trim(),
        addressRequestDto: {
          streetNumber: address.streetNumber.trim() || null,
          streetName: address.streetName.trim() || null,
          postalCode: address.postalCode.trim() || null,
          state: address.state.trim() || null,
          country: address.country.trim() || null,
        },
      };

      const response = await contactAPI.createContact(contactData);
      const newContact = response.data;
      setContacts((prev) => [newContact, ...prev]);
      setFirstName("");
      setLastName("");
      setPhone("");
      setAddress({
        streetNumber: "",
        streetName: "",
        postalCode: "",
        state: "",
        country: "",
      });
      setIsFormOpen(false);
    } catch (err) {
      console.error("Error adding contact:", err);
      const errorMsg = err.response?.data?.message || "Error adding contact";
      setError(errorMsg);
    }
  };

  const handleDelete = async (id) => {
    try {
      await contactAPI.deleteContact(id);
      setContacts((prev) => prev.filter((c) => c.id !== id));
      setError("");
    } catch (err) {
      console.error("Error deleting contact:", err);
      const errorMsg = err.response?.data?.message || "Error deleting contact";
      setError(errorMsg);
    }
  };

  return (
    <main className="phonebook-main">
      {error && <div className="error-message">{error}</div>}

      {/* Add Contact Form */}
      <div className={`add-contact-card ${isFormOpen ? "open" : ""}`}>
        {!isFormOpen ? (
          <button
            type="button"
            className="add-contact-btn-open"
            onClick={() => setIsFormOpen(true)}
          >
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
              <path d="M12 5v14M5 12h14" />
            </svg>
            Add New Contact
          </button>
        ) : (
          <form className="contact-form" onSubmit={handleAddContact}>
            <h2 className="form-title">Add New Contact</h2>
            <div className="form-row">
              <input type="text" placeholder="First Name *" value={firstName} onChange={(e) => setFirstName(e.target.value)} className="form-input" />
              <input type="text" placeholder="Last Name" value={lastName} onChange={(e) => setLastName(e.target.value)} className="form-input" />
            </div>
            <input type="tel" placeholder="Phone Number *" value={phone} onChange={(e) => setPhone(e.target.value)} className="form-input full-width" />

            <h3 className="form-section-title">Address (Optional)</h3>
            <div className="form-row">
              <input type="text" placeholder="Street Number" value={address.streetNumber} onChange={(e) => setAddress({ ...address, streetNumber: e.target.value })} className="form-input" />
              <input type="text" placeholder="Street Name" value={address.streetName} onChange={(e) => setAddress({ ...address, streetName: e.target.value })} className="form-input" />
            </div>
            <div className="form-row">
              <input type="text" placeholder="Postal Code" value={address.postalCode} onChange={(e) => setAddress({ ...address, postalCode: e.target.value })} className="form-input" />
              <input type="text" placeholder="State" value={address.state} onChange={(e) => setAddress({ ...address, state: e.target.value })} className="form-input" />
            </div>
            <input type="text" placeholder="Country" value={address.country} onChange={(e) => setAddress({ ...address, country: e.target.value })} className="form-input full-width" />
            <div className="form-buttons">
              <button type="submit" className="btn btn-primary">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                  <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2" />
                  <circle cx="9" cy="7" r="4" />
                  <line x1="19" x2="19" y1="8" y2="14" />
                  <line x1="22" x2="16" y1="11" y2="11" />
                </svg>
                Add Contact
              </button>
              <button type="button" className="btn btn-secondary" onClick={() => setIsFormOpen(false)}>
                Cancel
              </button>
            </div>
          </form>
        )}
      </div>

      {/* Search Bar */}
      <div className="search-card">
        <svg className="search-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
          <circle cx="11" cy="11" r="8" />
          <path d="m21 21-4.35-4.35" />
        </svg>
        <input type="search" placeholder="Search by name, phone, or address..." value={searchQuery} onChange={(e) => setSearchQuery(e.target.value)} className="search-input" />
      </div>

      {/* Contact List */}
      {loading ? (
        <div className="loading-state">
          <div className="spinner"></div>
          <p>Loading contacts...</p>
        </div>
      ) : filteredContacts.length === 0 ? (
        <div className="empty-state">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
            <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2" />
            <circle cx="12" cy="7" r="4" />
            <line x1="12" y1="12" x2="12" y2="18" />
            <line x1="9" y1="15" x2="15" y2="15" />
          </svg>
          <h3>No Contacts Yet</h3>
          <p>{searchQuery.trim() ? "No contacts match your search" : "Add your first contact to get started"}</p>
        </div>
      ) : (
        <div className="contact-list">
          <div className="contacts-count">{filteredContacts.length} contact{filteredContacts.length !== 1 ? "s" : ""}</div>
          {filteredContacts.map((contact) => (
            <div key={contact.id} className="contact-card">
              <div className="contact-info">
                <div className="contact-name">
                  <span className="first-name">{contact.firstName}</span>
                  {contact.lastName && <span className="last-name">{contact.lastName}</span>}
                </div>
                {contact.phoneNumber && (
                  <div className="contact-phone-row">
                    <svg className="phone-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                      <path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z" />
                    </svg>
                    <span className="contact-phone">{contact.phoneNumber}</span>
                  </div>
                )}
                {contact.addressRequestDto && (contact.addressRequestDto.streetNumber || contact.addressRequestDto.streetName || contact.addressRequestDto.postalCode || contact.addressRequestDto.state || contact.addressRequestDto.country) && (
                  <div className="contact-address-row">
                    <svg className="address-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                      <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z" />
                      <circle cx="12" cy="10" r="3" />
                    </svg>
                    <span className="contact-address">
                      {[
                        contact.addressRequestDto.streetNumber,
                        contact.addressRequestDto.streetName,
                        contact.addressRequestDto.postalCode,
                        contact.addressRequestDto.state,
                        contact.addressRequestDto.country
                      ]
                        .filter(Boolean)
                        .join(", ")}
                    </span>
                  </div>
                )}
              </div>
              <button type="button" className="delete-btn" onClick={() => handleDelete(contact.id)} aria-label={`Delete ${contact.firstName} ${contact.lastName || ""}`}>
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                  <polyline points="3 6 5 6 21 6" />
                  <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2" />
                  <line x1="10" y1="11" x2="10" y2="17" />
                  <line x1="14" y1="11" x2="14" y2="17" />
                </svg>
              </button>
            </div>
          ))}
        </div>
      )}
    </main>
  );
}

export default Main;
