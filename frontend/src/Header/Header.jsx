function Header() {
  return (
    <header className="phonebook-header">
      <div className="header-icon">
        <svg
          viewBox="0 0 64 64"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
          aria-hidden="true"
        >
          <rect
            width="48"
            height="56"
            x="8"
            y="4"
            rx="4"
            fill="none"
            stroke="currentColor"
            strokeWidth="3"
          />
          <path
            d="M8 20h48"
            stroke="currentColor"
            strokeWidth="3"
            strokeLinecap="round"
          />
          <circle cx="32" cy="36" r="8" fill="currentColor" />
          <path
            d="M28 44c0-2.5 1.5-4 4-4s4 1.5 4 4"
            stroke="currentColor"
            strokeWidth="2"
            strokeLinecap="round"
          />
        </svg>
      </div>
      <h1 className="header-title">Phone Book</h1>
      <p className="header-subtitle">Manage your contacts easily</p>
    </header>
  );
}

export default Header;
