# Quick Start Guide - Beautiful Phonebook Frontend

## Getting Started in 3 Steps

### Step 1: Install Dependencies

```bash
cd frontend
npm install
```

### Step 2: Start Backend Server

Make sure your Spring Boot backend is running on `http://localhost:8080`

```bash
cd backend
./mvnw spring-boot:run
# or on Windows:
mvnw.cmd spring-boot:run
```

### Step 3: Start Frontend Development Server

```bash
npm run dev
```

The frontend will be available at `http://localhost:5173`

---

## Features Overview

### 📋 Add Contact
1. Click **"Add New Contact"** button
2. Enter **First Name** (required)
3. Enter **Last Name** (optional)
4. Enter **Phone Number** (required)
5. Enter **Address** (optional)
6. Click **"Add Contact"** button

### 🔍 Search Contacts
- Type in the search bar to find contacts
- Searches by: First Name, Last Name, Phone, or Address
- Results update in real-time

### 🗑️ Delete Contact
- Click the **delete icon** on any contact card
- Contact will be removed immediately

---

## What You'll See

### Beautiful Header
- Large phonebook icon with shadow
- Gradient title text
- Subtitle

### Clean Contact Form
- Expands on click
- Two-column layout for first/last name
- Phone and address fields
- Form validation with error messages

### Modern Search Bar
- Icon-integrated search input
- Real-time filtering

### Organized Contact Cards
- Contact name displayed prominently
- Phone number with icon
- Address with icon
- Delete button on the right
- Hover effects for interactivity

### Responsive Design
- Works perfectly on mobile (< 480px)
- Tablet and desktop optimized
- Touch-friendly buttons

---

## API Endpoints Used

The frontend connects to these backend endpoints:

| Method | Endpoint | Purpose |
|--------|----------|---------|
| GET | `/api/contacts` | Fetch all contacts |
| POST | `/api/contacts` | Create new contact |
| DELETE | `/api/contacts/{id}` | Delete contact |

**Example Contact JSON:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "phone": "(555) 123-4567",
  "address": "123 Main St, City, ST 12345"
}
```

---

## Troubleshooting

### "Unable to connect to server" Error
- ❌ Make sure backend is running on `http://localhost:8080`
- ✅ Check the backend logs for errors
- ✅ Try refreshing the page

### Form fields not submitting
- ❌ First Name is required
- ❌ Phone Number is required
- ✅ Fill both required fields and try again

### Search not working
- ✅ Type to see real-time results
- ✅ Clear search to see all contacts
- ✅ Search works across all fields

### Contact not deleting
- ❌ Check backend is still running
- ✅ Try refreshing the page
- ✅ Check browser console for errors

---

## Build for Production

```bash
npm run build
```

This creates an optimized production build in the `dist/` folder.

---

## Project Structure

```
frontend/
├── src/
│   ├── App.jsx           # Main component with routing
│   ├── index.css         # Global styles & variables
│   ├── main.jsx          # React entry point
│   ├── Header/           # Header component
│   │   ├── Header.jsx
│   │   └── Header.css
│   ├── Main/             # Main phonebook logic
│   │   ├── Main.jsx
│   │   └── Main.css
│   └── Footer/           # Footer component
│       ├── Footer.jsx
│       └── Footer.css
├── public/               # Static files
├── index.html            # HTML template
├── package.json          # Dependencies
├── vite.config.js        # Vite configuration
└── eslint.config.js      # ESLint configuration
```

---

## Available Scripts

```bash
# Development server
npm run dev

# Build for production
npm run build

# Preview production build
npm run preview

# Lint code
npm run lint
```

---

## Browser DevTools Tips

### Debugging
- Open DevTools with F12
- Check Console tab for errors
- Use Network tab to see API calls
- Use Elements/Inspector to inspect components

### Testing Responsive Design
- Press Ctrl+Shift+M to toggle device toolbar
- Test different screen sizes
- Test touch interactions on mobile

---

## Features Implemented ✨

✅ First Name field (required)
✅ Last Name field (optional)
✅ Phone Number field (required)
✅ Address field (optional)
✅ Real-time search across all fields
✅ Add new contacts
✅ Delete contacts
✅ Beautiful, modern UI
✅ Responsive design (mobile, tablet, desktop)
✅ Smooth animations
✅ Error handling & validation
✅ Loading states
✅ Empty states
✅ Professional styling

---

## Next Steps

1. **Customize Colors**: Edit CSS variables in `src/index.css`
2. **Add Features**: Edit components in `src/Main/Main.jsx`
3. **Change Layout**: Modify CSS in `src/Main/Main.css`
4. **Add More Pages**: Create new components in `src/`

---

## Documentation Files

- `FRONTEND_README.md` - Detailed frontend documentation
- `UI_COMPONENTS.md` - Complete component guide
- `QUICK_START.md` - This file

---

## Support

For issues or questions:
1. Check the browser console for errors
2. Verify backend is running
3. Check network requests in DevTools
4. Review error messages in the UI

Enjoy your beautiful phonebook app! 📞✨

