import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-dashboard-tabs',
  standalone: true,
  imports: [
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './dashboard-tabs.html',
  styleUrls: ['./dashboard-tabs.css']
})
export class DashboardTabs {}
