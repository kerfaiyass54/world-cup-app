import { Component, OnInit, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Navbar } from './components/navbar/navbar';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Navbar],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App implements OnInit {
  protected readonly title = signal('wc-app-front');
  ngOnInit() {

    const theme = localStorage.getItem('theme');

    if (theme === 'dark') {

      document.body.classList.add('dark-mode');

    }

  }
}
