import { Component, signal } from '@angular/core'
import { RouterLink, RouterLinkActive } from '@angular/router'

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

  mobileMenu = signal(false)

  toggleMenu() {

    this.mobileMenu.update(v => !v)

  }

}
