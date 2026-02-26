# Address Object Implementation - Frontend

## Overview

The frontend has been updated to handle Address as a complex object with the following fields:
- **streetNumber** - Street number
- **streetName** - Street name
- **postalCode** - Postal code
- **state** - State
- **country** - Country

---

## Implementation Details

### 1. State Management

**File**: `src/Main/Main.jsx`

Address is now stored as an object in state:

```javascript
const [address, setAddress] = useState({
  streetNumber: "",
  streetName: "",
  postalCode: "",
  state: "",
  country: "",
});
```

### 2. Form Fields

The form now includes individual inputs for each address field:

```jsx
<h3 className="form-section-title">Address (Optional)</h3>
<div className="form-row">
  <input 
    type="text" 
    placeholder="Street Number" 
    value={address.streetNumber} 
    onChange={(e) => setAddress({ ...address, streetNumber: e.target.value })} 
    className="form-input" 
  />
  <input 
    type="text" 
    placeholder="Street Name" 
    value={address.streetName} 
    onChange={(e) => setAddress({ ...address, streetName: e.target.value })} 
    className="form-input" 
  />
</div>
<div className="form-row">
  <input 
    type="text" 
    placeholder="Postal Code" 
    value={address.postalCode} 
    onChange={(e) => setAddress({ ...address, postalCode: e.target.value })} 
    className="form-input" 
  />
  <input 
    type="text" 
    placeholder="State" 
    value={address.state} 
    onChange={(e) => setAddress({ ...address, state: e.target.value })} 
    className="form-input" 
  />
</div>
<input 
  type="text" 
  placeholder="Country" 
  value={address.country} 
  onChange={(e) => setAddress({ ...address, country: e.target.value })} 
  className="form-input full-width" 
/>
```

### 3. Form Submission

When submitting, the address object is properly structured:

```javascript
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
```

### 4. Contact Display

Contacts display the address object formatted as a readable string:

```javascript
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
```

This displays only the fields that have values, separated by commas.

### 5. Search Functionality

Search now includes address fields:

```javascript
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
```

Users can search by any address field:
- Street number
- Street name
- Postal code
- State
- Country

### 6. Form Reset

After successfully adding a contact, the address object is reset:

```javascript
setAddress({
  streetNumber: "",
  streetName: "",
  postalCode: "",
  state: "",
  country: "",
});
```

---

## Backend Alignment

### API Request Format

The frontend sends data to the backend in this format:

```json
POST /api/contacts
{
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "(555) 123-4567",
  "addressRequestDto": {
    "streetNumber": "123",
    "streetName": "Main Street",
    "postalCode": "12345",
    "state": "CA",
    "country": "USA"
  }
}
```

### API Response Format

The backend responds with contacts that include the address object:

```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "(555) 123-4567",
  "addressRequestDto": {
    "streetNumber": "123",
    "streetName": "Main Street",
    "postalCode": "12345",
    "state": "CA",
    "country": "USA"
  }
}
```

---

## CSS Styling

New CSS class added for the address section:

```css
.form-section-title {
  font-size: 1rem;
  font-weight: 600;
  color: #4a5568;
  margin-top: 1rem;
  margin-bottom: 0.75rem;
}
```

---

## Form Layout

The address form is organized in rows:

1. **Row 1**: Street Number, Street Name
2. **Row 2**: Postal Code, State
3. **Row 3**: Country (full width)

This creates a clean, organized form layout:

```
┌─────────────────────────────────────────┐
│ Street Number      │ Street Name         │
├─────────────────────────────────────────┤
│ Postal Code        │ State               │
├─────────────────────────────────────────┤
│ Country                                 │
└─────────────────────────────────────────┘
```

---

## Contact Card Display

When displaying contacts, the address is shown as:

```
Name: John Doe
📞 (555) 123-4567
📍 123, Main Street, 12345, CA, USA
```

Only populated fields are displayed, separated by commas.

---

## Key Features

✅ **Structured Address Object** - All address fields are part of an object  
✅ **Individual Inputs** - Each address field has its own input  
✅ **Flexible Display** - Only shows populated fields  
✅ **Searchable** - Can search by any address field  
✅ **Optional Fields** - All address fields are optional  
✅ **Clean Layout** - Address section clearly separated in form  
✅ **Backend Aligned** - Matches AddressRequestDto from backend  

---

## Data Flow

```
User fills form with:
├─ firstName (required)
├─ lastName (optional)
├─ phoneNumber (required)
└─ addressRequestDto (optional)
    ├─ streetNumber (optional)
    ├─ streetName (optional)
    ├─ postalCode (optional)
    ├─ state (optional)
    └─ country (optional)
       ↓
Frontend validates:
├─ firstName is not empty ✓
├─ phoneNumber is not empty ✓
└─ Address fields are trimmed ✓
       ↓
Axios sends POST /api/contacts with JSON body
       ↓
Backend receives and validates:
├─ firstName required validation
├─ phoneNumber required validation
├─ Address fields optional
       ↓
Database saves contact with address
       ↓
Response returns contact with all fields
       ↓
Frontend displays contact with address fields
```

---

## Testing the Address Object

### Test 1: Add Contact with Full Address

1. Open form
2. Fill all fields including address
3. Click "Add Contact"
4. Verify contact displays with all address fields

**Expected Output**:
```
John Doe
📞 (555) 123-4567
📍 123, Main Street, 12345, CA, USA
```

### Test 2: Add Contact without Address

1. Open form
2. Fill only firstName, lastName, phoneNumber
3. Skip all address fields
4. Click "Add Contact"
5. Verify contact displays without address section

**Expected Output**:
```
John Doe
📞 (555) 123-4567
```

### Test 3: Search by Address Field

1. Add contact with address
2. Type in search box: "123" (street number)
3. Verify contact appears in filtered list
4. Type "CA" (state)
5. Verify contact appears in filtered list

### Test 4: Partial Address

1. Fill some address fields (e.g., only country and state)
2. Add contact
3. Verify only filled fields display

**Expected Output**:
```
John Doe
📞 (555) 123-4567
📍 CA, USA
```

---

## Files Updated

| File | Changes |
|------|---------|
| `src/Main/Main.jsx` | Added address object state, form fields, display logic, search logic |
| `src/Main/Main.css` | Added `.form-section-title` styling |

---

## Backend Compatibility

✅ Frontend `addressRequestDto` matches Backend `AddressRequestDto`  
✅ All field names match exactly  
✅ Field types match (String for all)  
✅ Optional fields are handled correctly  

---

## Summary

The Address object is now fully implemented on the frontend with:
- ✅ Separate state management
- ✅ Individual form inputs
- ✅ Proper serialization for backend
- ✅ Flexible display logic
- ✅ Searchable address fields
- ✅ Clean form layout
- ✅ Perfect backend alignment

**Ready for production!** 🚀


