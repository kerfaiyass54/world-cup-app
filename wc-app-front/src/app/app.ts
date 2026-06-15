import { RouterOutlet } from '@angular/router';
import { Navbar } from './layout/navbar/navbar';
import { Component, OnInit, signal } from '@angular/core';
import { UserProfileService } from './user-profile.service';
import Keycloak from 'keycloak-js';


@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Navbar],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App implements OnInit {

  currentUserId = '';

  constructor(
    private userProfileService: UserProfileService,
    private keycloak: Keycloak
  ) {}

  ngOnInit(): void {

    const email =
      this.keycloak.tokenParsed?.['email'] as string;

    this.userProfileService
      .saveUser({ email })
      .subscribe(user => {

        this.currentUserId = user.id;

        localStorage.setItem('userId', user.id);

        console.log('User saved:', user);
      });
  }
}
