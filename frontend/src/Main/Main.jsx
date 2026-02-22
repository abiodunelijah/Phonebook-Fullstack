import { useState, useMemo } from "react";

const INITIAL_CONTACTS = [
  { id: 1, name: "John Smith", phone: "(555) 123-4567" },
  { id: 2, name: "Sarah Johnson", phone: "(555) 234-5678" },
  { id: 3, name: "Michael Brown", phone: "(555) 345-6789" },
];

function Main() {
  const [contacts, setContacts] = useState(INITIAL_CONTACTS);
  const [name, setName] = useState("");
  const [phone, setPhone] = useState("");
  const [searchQuery, setSearchQuery] = useState("");

  const filteredContacts = useMemo(() => {
    if (!searchQuery.trim()) return contacts;
    const q = searchQuery.toLowerCase();
    return contacts.filter(
      (c) =>
        c.name.toLowerCase().includes(q) || c.phone.includes(searchQuery)
    );
  }, [contacts, searchQuery]);

  const handleAddContact = (e) => {
    e.preventDefault();
    if (!name.trim() || !phone.trim()) return;
    const newContact = {
      id: Date.now(),
      name: name.trim(),
      phone: phone.trim(),
    };
    setContacts((prev) => [newContact, ...prev]);
    setName("");
    setPhone("");
  };

  const handleDelete = (id) => {
    setContacts((prev) => prev.filter((c) => c.id !== id));
  };

  return (
    <main className="phonebook-main">
      <form className="add-contact-card" onSubmit={handleAddContact}>
        <input
          type="text"
          placeholder="Name"
          value={name}
          onChange={(e) => setName(e.target.value)}
          className="add-contact-input"
        />
        <input
          type="tel"
          placeholder="Phone Number"
          value={phone}
          onChange={(e) => setPhone(e.target.value)}
          className="add-contact-input"
        />
        <button type="submit" className="add-contact-btn">
          <svg
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            strokeWidth="2"
            strokeLinecap="round"
            strokeLinejoin="round"
          >
            <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2" />
            <circle cx="9" cy="7" r="4" />
            <line x1="19" x2="19" y1="8" y2="14" />
            <line x1="22" x2="16" y1="11" y2="11" />
          </svg>
          Add Contact
        </button>
      </form>

      <div className="search-card">
        <svg
          className="search-icon"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          strokeWidth="2"
          strokeLinecap="round"
          strokeLinejoin="round"
        >
          <circle cx="11" cy="11" r="8" />
          <path d="m21 21-4.35-4.35" />
        </svg>
        <input
          type="search"
          placeholder="Search contacts..."
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
          className="search-input"
        />
      </div>

      <div className="contact-list">
        {filteredContacts.map((contact) => (
          <div key={contact.id} className="contact-card">
            <div className="contact-info">
              <span className="contact-name">{contact.name}</span>
              <div className="contact-phone-row">
                <svg
                  className="phone-icon"
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  strokeWidth="2"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                >
                  <path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z" />
                </svg>
                <span className="contact-phone">{contact.phone}</span>
              </div>
            </div>
            <button
              type="button"
              className="delete-btn"
              onClick={() => handleDelete(contact.id)}
              aria-label={`Delete ${contact.name}`}
            >
              <svg
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                strokeWidth="2"
                strokeLinecap="round"
                strokeLinejoin="round"
              >
                <polyline points="3 6 5 6 21 6" />
                <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2" />
                <line x1="10" y1="11" x2="10" y2="17" />
                <line x1="14" y1="11" x2="14" y2="17" />
              </svg>
            </button>
          </div>
        ))}
      </div>
    </main>
  );
}

export default Main;
