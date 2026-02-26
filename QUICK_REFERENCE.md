# 🚀 Phonebook Frontend - Quick Reference Card

## Start Here

```bash
# Terminal 1: Start Frontend
cd frontend
npm install
npm run dev

# Terminal 2: Start Backend
cd backend
./mvnw spring-boot:run
```

**Open**: http://localhost:5173

---

## Feature Quick Links

### ✨ Features
| Feature | Where | How |
|---------|-------|-----|
| Add Contact | Main Form | Click "Add New Contact" button |
| Search | Search Bar | Type name/phone/address |
| Delete | Contact Card | Click red delete icon |
| Required Fields | Form | First Name & Phone Number |
| Optional Fields | Form | Last Name & Address |

---

## File Structure Quick Reference

```
frontend/src/
├── App.jsx               ← Main app (imports components)
├── index.css             ← Global styles & colors
├── Header/
│   ├── Header.jsx        ← Title & icon
│   └── Header.css        ← Header styling
├── Main/
│   ├── Main.jsx          ← Phonebook logic & form
│   └── Main.css          ← Phonebook styling
└── Footer/
    ├── Footer.jsx        ← Footer
    └── Footer.css        ← Footer styling
```

---

## Component Tree

```
App
├── Header
│   ├── Icon (SVG)
│   ├── Title
│   └── Subtitle
├── Main
│   ├── Error Message (conditional)
│   ├── Add Contact Form (collapsible)
│   │   ├── First Name Input
│   │   ├── Last Name Input
│   │   ├── Phone Input
│   │   ├── Address Input
│   │   └── Buttons (Add, Cancel)
│   ├── Search Bar
│   │   ├── Icon
│   │   └── Search Input
│   └── Contact List (conditional)
│       ├── Loading State
│       ├── Empty State
│       └── Contact Cards
│           ├── Name (First + Last)
│           ├── Phone with Icon
│           ├── Address with Icon
│           └── Delete Button
└── Footer
```

---

## Color Palette (CSS Variables)

```css
--primary-color: #3182ce          /* Blue - Main buttons */
--primary-dark: #2563eb           /* Dark blue - Hover */
--primary-light: #e6f2ff          /* Light blue - Background */
--secondary-color: #4299e1        /* Lighter blue */
--danger-color: #e53e3e           /* Red - Delete */
--text-primary: #2d3748           /* Dark gray - Text */
--text-secondary: #718096         /* Medium gray - Subtext */
--border-color: #e2e8f0           /* Light gray - Borders */
--bg-light: #f7fafc               /* Very light gray - BG */
--white: #ffffff                  /* White - Card BG */
```

---

## API Endpoints

| Method | Endpoint | Purpose | Body |
|--------|----------|---------|------|
| GET | /api/contacts | Fetch all | - |
| POST | /api/contacts | Create | Contact JSON |
| DELETE | /api/contacts/{id} | Delete | - |

### Contact JSON Format

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "phone": "(555) 123-4567",
  "address": "123 Main St, City, ST 12345"
}
```

---

## State Management (Main.jsx)

```javascript
// Contact data
const [contacts, setContacts] = useState([])
const [firstName, setFirstName] = useState("")
const [lastName, setLastName] = useState("")
const [phone, setPhone] = useState("")
const [address, setAddress] = useState("")

// UI state
const [searchQuery, setSearchQuery] = useState("")
const [loading, setLoading] = useState(true)
const [error, setError] = useState("")
const [isFormOpen, setIsFormOpen] = useState(false)

// Derived state
const filteredContacts = useMemo(...)  // Auto-filters on search
```

---

## Key Functions

```javascript
// Fetch all contacts from API
fetchContacts()

// Add new contact to API
handleAddContact(e)

// Remove contact from API & UI
handleDelete(id)

// Filter contacts by search query
filteredContacts (useMemo hook)
```

---

## CSS Classes Reference

### Form
- `.add-contact-card` - Form container
- `.add-contact-btn-open` - Add button (closed state)
- `.contact-form` - Form wrapper (open state)
- `.form-title` - Form heading
- `.form-row` - Two-column layout
- `.form-input` - Input field
- `.form-buttons` - Button container
- `.btn`, `.btn-primary`, `.btn-secondary` - Buttons

### Search
- `.search-card` - Search container
- `.search-icon` - Magnifying glass icon
- `.search-input` - Search input field

### Contacts
- `.contact-list` - List container
- `.contact-card` - Single contact card
- `.contact-name` - Name display
- `.contact-phone-row` - Phone with icon
- `.contact-address-row` - Address with icon
- `.delete-btn` - Delete button

### States
- `.loading-state` - Loading indicator
- `.empty-state` - No contacts message
- `.error-message` - Error alert
- `.spinner` - Loading spinner animation

---

## Responsive Breakpoints

```css
/* Mobile First */
Default styles for mobile

/* Tablet */
@media (min-width: 480px)

/* Desktop */
@media (min-width: 768px) and beyond
```

---

## Animation Timings

| Animation | Duration | Easing |
|-----------|----------|--------|
| Form toggle | 0.3s | ease |
| Card hover | 0.2s | ease |
| Header entrance | 0.5s | ease |
| Spinner | 0.6s | linear |
| Error fade | 0.3s | ease |

---

## Keyboard Shortcuts & Actions

| Action | Trigger |
|--------|---------|
| Open form | Click "Add New Contact" |
| Close form | Click "Cancel" |
| Submit form | Click "Add Contact" or Enter |
| Search | Type in search bar |
| Clear search | Delete search text |
| Delete contact | Click red delete icon |

---

## Common Issues & Solutions

| Issue | Solution |
|-------|----------|
| Backend connection error | Start backend on port 8080 |
| Form not submitting | Fill required fields (First Name, Phone) |
| Search not working | Check field names match data |
| Styling looks different | Clear browser cache (Ctrl+Shift+Del) |
| Mobile layout broken | Check viewport meta tag in HTML |

---

## Performance Tips

- **Search**: Uses useMemo for efficient filtering
- **Animations**: CSS-based (hardware accelerated)
- **Rendering**: Functional components with hooks
- **Bundle**: ~60-80KB gzipped (small size)

---

## Customization Quick Guide

### Change Colors
Edit `/src/index.css` CSS variables

### Change Font Size
Edit typography in respective `.css` files

### Add Form Fields
1. Add state in Main.jsx: `const [newField, setNewField] = useState("")`
2. Add input in form: `<input value={newField} onChange={...} />`
3. Update POST body with new field

### Change API URL
Edit in `Main.jsx`: `const API_BASE_URL = "http://..."`

### Change Search Fields
Edit filter in `Main.jsx` useMemo hook

---

## Testing Checklist

- [ ] Add contact with all fields
- [ ] Add contact with only required fields
- [ ] Search by first name
- [ ] Search by last name
- [ ] Search by phone number
- [ ] Search by address
- [ ] Delete a contact
- [ ] Form validation (empty required field)
- [ ] API integration
- [ ] Mobile responsiveness (< 480px)
- [ ] Tablet view (480-768px)
- [ ] Desktop view (> 768px)

---

## Documentation Index

| Doc | Purpose |
|-----|---------|
| QUICK_START.md | Getting started guide |
| FRONTEND_README.md | Feature documentation |
| UI_COMPONENTS.md | Component specifications |
| DESIGN_GUIDE.md | Visual design & mockups |
| IMPLEMENTATION_SUMMARY.md | Complete overview |
| CHANGES_SUMMARY.md | What was changed |
| COMPLETION_CHECKLIST.md | Feature checklist |
| QUICK_REFERENCE.md | This file |

---

## Version Info

- **Frontend**: React 19.2.0
- **Build Tool**: Vite 7.2.4
- **Node**: v14+ recommended
- **Status**: ✅ Production Ready
- **Date**: February 27, 2026

---

## Support Resources

1. **Check Docs**: Read FRONTEND_README.md
2. **See Examples**: Review UI_COMPONENTS.md
3. **Design Help**: Consult DESIGN_GUIDE.md
4. **Debug**: Check browser DevTools (F12)
5. **Network**: Check Network tab for API calls

---

## Quick Commands

```bash
# Development
npm run dev

# Production build
npm run build

# Preview build
npm run preview

# Lint code
npm run lint
```

---

**Need Help?** Check the documentation files or review the code comments in the components! 📚

Happy coding! 🎉

