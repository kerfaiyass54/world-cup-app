import {
  Component,
  OnInit,
  signal,
  inject
} from '@angular/core';

import {
  RouterLink,
  RouterLinkActive
} from '@angular/router';

import { keycloak } from '../../keycloak.config';
import { UserProfileService } from '../../user-profile.service';


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
export class Navbar implements OnInit {

  private userProfileService =
    inject(UserProfileService);

  mobileMenu = signal(false);

  profileMenuOpen = signal(false);

  hasProfile = signal(false);

  userEmail = signal(
    sessionStorage.getItem('email') ?? ''
  );

  ngOnInit(): void {

    const email = this.userEmail();

    if (!email) {
      return;
    }

    this.userProfileService
      .hasProfile(email)
      .subscribe({

        next: (response) => {

          this.hasProfile.set(response);

        },

        error: (error) => {

          console.error(
            'Failed to check profile',
            error
          );

        }

      });

  }

  toggleMenu(): void {

    this.mobileMenu.update(
      value => !value
    );

  }

  toggleProfileMenu(): void {

    this.profileMenuOpen.update(
      value => !value
    );

  }

  async logout(): Promise<void> {

    sessionStorage.clear();

    await keycloak.logout({
      redirectUri: window.location.origin
    });

  }
}
