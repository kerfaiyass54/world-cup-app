import { Component, signal } from '@angular/core'
import { WcInfos } from './wc-infos/wc-infos';
import { TournamentsInfos } from './tournaments-infos/tournaments-infos';
import { TeamsInfos } from './teams-infos/teams-infos';
import { GeneralInfo } from './general-info/general-info';

@Component({
  selector: 'dashboard',

  standalone: true,

  templateUrl: './dashboard.html',
  imports: [WcInfos, TournamentsInfos, TeamsInfos, GeneralInfo],

  styleUrl: './dashboard.css',
})
export class Dashboard {
  activeTab = signal('wc');

  select(tab: string) {
    this.activeTab.set(tab);
  }
}
