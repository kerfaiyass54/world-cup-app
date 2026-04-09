import {
  Component,
  AfterViewInit,
  ElementRef
} from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.html',
  styleUrl: './home-page.css',
  imports: [RouterLink],
})
export class HomePage implements AfterViewInit {
  constructor(private el: ElementRef) {}

  ngAfterViewInit() {
    const sections = this.el.nativeElement.querySelectorAll('.reveal');

    const observer = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          if (entry.isIntersecting) {
            entry.target.classList.add('active');
          }
        });
      },
      {
        threshold: 0.6,
      },
    );

    sections.forEach((section: Element) => observer.observe(section));
  }
}
