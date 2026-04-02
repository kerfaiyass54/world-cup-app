import { Component, AfterViewInit } from '@angular/core';

@Component({
  selector: 'app-welcome',
  standalone: true,
  templateUrl: './welcome.html',
  styleUrl: './welcome.css'
})
export class Welcome implements AfterViewInit {

  ngAfterViewInit(): void {

    const sections = document.querySelectorAll('.fade-section');

    const observer = new IntersectionObserver(entries => {

      entries.forEach(entry => {

        if (entry.isIntersecting) {

          entry.target.classList.add('show');

        }

      });

    }, {

      threshold: 0.25

    });

    sections.forEach(section => observer.observe(section));

  }

}
