import {
  Component,
  inject,
  OnInit,
  signal
} from '@angular/core';

import { Router } from '@angular/router';

import {
  Profile,
  UserProfileService
} from '../../user-profile.service';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [],
  templateUrl: './user-profile.html',
  styleUrl: './user-profile.css',
})
export class UserProfile implements OnInit {

  private userProfileService =
    inject(UserProfileService);

  private router =
    inject(Router);

  loading = signal(true);

  error = signal('');

  profile = signal<Profile | null>(
    null
  );

  ngOnInit(): void {

    const email =
      sessionStorage.getItem('email');

    if (!email) {

      this.error.set(
        'User email not found'
      );

      this.loading.set(false);

      return;

    }

    this.userProfileService
      .getProfileByEmail(email)
      .subscribe({

        next: (profile) => {

          this.profile.set(profile);

          this.loading.set(false);

        },

        error: () => {

          this.error.set(
            'Failed to load profile'
          );

          this.loading.set(false);

        }

      });

  }

  editProfile(): void {

    const profile =
      this.profile();

    if (!profile) {

      return;

    }

    this.router.navigate([
      '/profile-add'
    ]);

  }

}
