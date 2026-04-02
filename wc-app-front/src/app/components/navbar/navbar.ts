import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './navbar.html',
  styleUrls: ['./navbar.css']
})
export class Navbar {

  toggleDarkMode() {

    document.body.classList.toggle('dark-mode');

    const mode =
      document.body.classList.contains('dark-mode')
        ? 'dark'
        : 'light';

    localStorage.setItem('theme', mode);

  }

}
