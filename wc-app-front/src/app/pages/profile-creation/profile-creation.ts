import {
  Component,
  inject,
  signal
} from '@angular/core';

import { Router } from '@angular/router';

import {
  ProfileDTO,
  UserProfileService
} from '../../user-profile.service';

@Component({
  selector: 'app-profile-creation',
  standalone: true,
  imports: [],
  templateUrl: './profile-creation.html',
  styleUrl: './profile-creation.css'
})
export class ProfileCreation {

  private router = inject(Router);

  private userProfileService =
    inject(UserProfileService);

  loading = signal(false);

  error = signal('');

  profile = signal<ProfileDTO>({
    name: '',
    nationality: '',
    description: '',
    living: true
  });

  updateField(
    field: keyof ProfileDTO,
    event: Event
  ): void {

    const target =
      event.target as HTMLInputElement;

    this.profile.update(current => ({
      ...current,

      [field]:
        field === 'living'
          ? target.checked
          : target.value
    }));

  }

  createProfile(): void {

    const email =
      sessionStorage.getItem('email');

    if (!email) {

      this.error.set(
        'User email not found'
      );

      return;

    }

    const profile =
      this.profile();

    if (
      !profile.name.trim() ||
      !profile.nationality.trim() ||
      !profile.description.trim()
    ) {

      this.error.set(
        'Please complete all fields'
      );

      return;

    }

    this.loading.set(true);

    this.userProfileService
      .saveProfile(
        email,
        profile
      )
      .subscribe({

        next: () => {

          this.loading.set(false);

          this.router.navigate([
            '/profile-check'
          ]);

        },

        error: (err) => {

          console.error(err);

          this.loading.set(false);

          this.error.set(
            'Failed to create profile'
          );

        }

      });

  }
}
