import { Outlet } from 'react-router-dom';
import { NavBar } from '../components/NavBar';

export function AppLayout() {
  return (
    <div className="app-shell">
      <NavBar />
      <main className="content">
        <Outlet />
      </main>
    </div>
  );
}
