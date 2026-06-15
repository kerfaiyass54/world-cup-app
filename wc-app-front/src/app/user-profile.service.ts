import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface UserDTO {
  email: string;
}

export interface ProfileDTO {
  name: string;
  nationality: string;
  description: string;
  living: boolean;
}

export interface User {
  id: string;
  email: string;
}

export interface Profile {
  id: string;
  userId: string;
  name: string;
  nationality: string;
  description: string;
  living: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class UserProfileService {

  private http = inject(HttpClient);

  private apiUrl = 'http://localhost:8070/api';

  saveUser(user: UserDTO): Observable<User> {
    return this.http.post<User>(
      `${this.apiUrl}/users`,
      user
    );
  }

  saveProfile(
    userId: string,
    profile: ProfileDTO
  ): Observable<Profile> {
    return this.http.post<Profile>(
      `${this.apiUrl}/users/${userId}/profile`,
      profile
    );
  }

  editProfile(
    profileId: string,
    profile: ProfileDTO
  ): Observable<Profile> {
    return this.http.put<Profile>(
      `${this.apiUrl}/profiles/${profileId}`,
      profile
    );
  }
}
