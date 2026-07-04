import { Component, signal } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { keycloak } from '../../keycloak.config';

@Component({
  selector: 'navbar',
  standalone: true,
  imports: [
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css'
})
export class Navbar {

  mobileMenu = signal(false);

  profileMenuOpen = signal(false);

  userEmail = signal(
    sessionStorage.getItem('email') ?? ''
  );

  toggleMenu() {
    this.mobileMenu.update(v => !v);
  }

  toggleProfileMenu() {
    this.profileMenuOpen.update(v => !v);
  }

  async logout() {

    sessionStorage.clear();

    await keycloak.logout({
      redirectUri: window.location.origin
    });

  }
}
