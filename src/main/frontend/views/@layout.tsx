import {NavLink, Outlet} from "react-router-dom";

export default function Layout() {
    return (
        <div className={'p-m'}>
            <nav>
                <NavLink className="btn btn-outline-primary text-decoration-none" to={'/'}>Home</NavLink>
                <NavLink className="btn btn-outline-primary text-decoration-none ms-2" to={'/chat'}>Chat</NavLink>
            </nav>
            <main>
                <Outlet></Outlet>
            </main>
        </div>
    )
}