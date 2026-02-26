# Beautiful Phonebook Frontend

A modern, responsive, and feature-rich phonebook application built with React and Vite.

## Features

✨ **Contact Management**
- Add contacts with First Name, Last Name, Phone Number, and Address
- Search contacts by any field (name, phone, address)
- Delete contacts with a single click
- Beautiful, intuitive UI with smooth animations

🎨 **Modern Design**
- Clean, minimalist interface with a beautiful gradient background
- Responsive design that works on mobile, tablet, and desktop
- Smooth animations and transitions for better user experience
- Professional color scheme with accessible typography

🔍 **Search Functionality**
- Real-time search across all contact fields
- Search by first name, last name, phone number, or address
- Live filtering as you type
- Shows result count for easy reference

📱 **Responsive UI**
- Mobile-friendly design
- Optimized for all screen sizes
- Touch-friendly buttons and inputs
- Adaptive grid layouts

## Project Structure

```
frontend/
├── src/
│   ├── App.jsx           # Main application component
│   ├── index.css         # Global styles
│   ├── main.jsx          # Entry point
│   ├── Header/
│   │   ├── Header.jsx    # Header component with title
│   │   └── Header.css    # Header styling
│   ├── Main/
│   │   ├── Main.jsx      # Main phonebook logic
│   │   └── Main.css      # Main styling
│   └── Footer/
│       ├── Footer.jsx    # Footer component
│       └── Footer.css    # Footer styling
├── package.json
├── vite.config.js
└── index.html
```

## Getting Started

### Installation

1. Install dependencies:
```bash
npm install
```

2. Start the development server:
```bash
npm run dev
```

3. Build for production:
```bash
npm run build
```

## Features in Detail

### Add Contact Form
- Collapsible form to keep the interface clean
- Required fields: First Name and Phone Number
- Optional fields: Last Name and Address
- Form validation with error messages
- Smooth form toggle with animations

### Contact Display
- Shows all contact information in an organized card layout
- Icons for phone and address for quick visual reference
- Separate display of first and last names
- Hover effects for better interactivity

### Search & Filter
- Real-time filtering as you type
- Searches across all contact fields
- Shows "No contacts" state when search yields no results
- Displays contact count for current view

### Error Handling
- Connection status feedback
- Loading states during data fetch
- Error messages for failed operations
- Graceful fallbacks for missing backend

## Styling Highlights

- **Color Palette**: Professional blues with accent colors
- **Typography**: Modern system fonts with clear hierarchy
- **Spacing**: Consistent padding and margins for visual balance
- **Shadows**: Subtle shadows for depth and layering
- **Animations**: Smooth transitions and entrance animations
- **Accessibility**: Proper contrast ratios and semantic HTML

## Backend Integration

The frontend connects to a backend API running on `http://localhost:8080/api`. Make sure your backend is running with the following endpoints:

- `GET /api/contacts` - Fetch all contacts
- `POST /api/contacts` - Create a new contact
- `DELETE /api/contacts/{id}` - Delete a contact

## Technologies Used

- **React 19** - UI framework
- **Vite** - Build tool and dev server
- **CSS3** - Modern styling with flexbox and grid
- **JavaScript ES6+** - Modern JavaScript features

## Browser Support

- Chrome/Edge (latest)
- Firefox (latest)
- Safari (latest)
- Mobile browsers

## Future Enhancements

- Edit contact functionality
- Contact categorization/groups
- Contact photo upload
- Export contacts to CSV/vCard
- Dark mode theme
- Contact favorites/pinning
- Bulk operations

## License

This project is part of the Phonebook-Fullstack application.

