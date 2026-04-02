import { Component } from '@angular/core';
import { Navbar } from '../../components/navbar/navbar';
import { DashboardTabs } from '../../components/dashboard-tabs/dashboard-tabs';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  imports: [Navbar, DashboardTabs, RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard {
  tabs = [
    {
      label: 'WC 2026',
      route: 'wc2026',
    },

    {
      label: 'Tournament Info',
      route: 'tournament',
    },

    {
      label: 'All Nations',
      route: 'nations',
    },

    {
      label: 'Head-to-Head',
      route: 'h2h',
    },
  ];
}
