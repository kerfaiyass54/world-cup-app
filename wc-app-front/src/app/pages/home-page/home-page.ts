import {
  Component,
  AfterViewInit,
  OnInit,
  ElementRef,
  ChangeDetectorRef,
  inject
} from '@angular/core';

import { RouterLink } from '@angular/router';

import {
  UserProfileService,
  UserDTO
} from '../../user-profile.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.html',
  styleUrl: './home-page.css',
  imports: [RouterLink],
})
export class HomePage implements OnInit, AfterViewInit {

  private userProfileService = inject(UserProfileService);
  private cdr = inject(ChangeDetectorRef);

  userCreated = false;

  constructor(private el: ElementRef) {}

  ngOnInit(): void {


  }

  ngAfterViewInit(): void {

    const sections =
      this.el.nativeElement.querySelectorAll('.reveal');

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
      }
    );

    sections.forEach(
      (section: Element) => observer.observe(section)
    );
  }
}
