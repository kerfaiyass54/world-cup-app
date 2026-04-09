import { Component, AfterViewInit, HostListener } from '@angular/core'
import { CommonModule } from '@angular/common'

@Component({
  selector: 'app-wc-infos',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './wc-infos.html',
  styleUrl: './wc-infos.css'
})
export class WcInfos implements AfterViewInit {

  /* ── Modal state ─────────────────────────────── */
  activeModal: string | null = null

  openModal(name: string)  { this.activeModal = name  }
  closeModal()             { this.activeModal = null  }

  // Close on Escape key
  @HostListener('document:keydown.escape')
  onEscape() { this.closeModal() }

  getContinentEmoji(name: string): string {
    return this.continents.find(c => c.name === name)?.emoji ?? ''
  }

  getTeams(name: string): string[] {
    return this.continents.find(c => c.name === name)?.teams ?? []
  }

  /* ── Data ────────────────────────────────────── */
  hosts = [
    { name: 'USA',    code: 'us' },
    { name: 'Canada', code: 'ca' },
    { name: 'Mexico', code: 'mx' }
  ]

  continents = [
    {
      name: 'Europe', emoji: '🌍',
      teams: [
        'France','Germany','Spain','Portugal','Belgium','Croatia',
        'Netherlands','Sweden','Norway','Austria','Scotland','England'
      ]
    },
    {
      name: 'South America', emoji: '🌎',
      teams: ['Brazil','Argentina','Uruguay','Colombia','Paraguay']
    },
    {
      name: 'Africa', emoji: '🌍',
      teams: [
        'Morocco','Senegal','Egypt','Algeria','Tunisia',
        'Ghana',"Côte d'Ivoire",'South Africa','Cabo Verde'
      ]
    },
    {
      name: 'Asia', emoji: '🌏',
      teams: ['Japan','Korea Republic','Saudi Arabia','Iran','Iraq','Jordan']
    }
  ]

  groups = [
    { name: 'A', teams: ['Mexico','South Africa','Korea Republic','Czechia'] },
    { name: 'B', teams: ['Canada','Bosnia','Qatar','Switzerland'] },
    { name: 'C', teams: ['Brazil','Morocco','Haiti','Scotland'] },
    { name: 'D', teams: ['USA','Paraguay','Australia','Türkiye'] },
    { name: 'E', teams: ['Germany','Curacao',"Côte d'Ivoire",'Ecuador'] },
    { name: 'F', teams: ['Netherlands','Japan','Sweden','Tunisia'] },
    { name: 'G', teams: ['Belgium','Egypt','Iran','New Zealand'] },
    { name: 'H', teams: ['Spain','Cabo Verde','Saudi Arabia','Uruguay'] },
    { name: 'I', teams: ['France','Senegal','Iraq','Norway'] },
    { name: 'J', teams: ['Argentina','Algeria','Austria','Jordan'] },
    { name: 'K', teams: ['Portugal','Congo DR','Uzbekistan','Colombia'] },
    { name: 'L', teams: ['England','Croatia','Ghana','Panama'] }
  ]

  flagMap: Record<string, string> = {
    'USA': 'us', 'Canada': 'ca', 'Mexico': 'mx',
    'France': 'fr', 'Germany': 'de', 'Spain': 'es',
    'Portugal': 'pt', 'Belgium': 'be', 'Croatia': 'hr',
    'Netherlands': 'nl', 'Sweden': 'se', 'Norway': 'no',
    'Austria': 'at', 'Scotland': 'gb-sct', 'England': 'gb-eng',
    'Brazil': 'br', 'Argentina': 'ar', 'Uruguay': 'uy',
    'Colombia': 'co', 'Paraguay': 'py',
    'Morocco': 'ma', 'Senegal': 'sn', 'Egypt': 'eg',
    'Algeria': 'dz', 'Tunisia': 'tn', 'Ghana': 'gh',
    "Côte d'Ivoire": 'ci', 'South Africa': 'za', 'Cabo Verde': 'cv',
    'Japan': 'jp', 'Korea Republic': 'kr', 'Saudi Arabia': 'sa',
    'Iran': 'ir', 'Iraq': 'iq', 'Jordan': 'jo',
    'Qatar': 'qa', 'Switzerland': 'ch', 'Bosnia': 'ba',
    'Türkiye': 'tr', 'Australia': 'au', 'Ecuador': 'ec',
    'Panama': 'pa', 'Congo DR': 'cd', 'Uzbekistan': 'uz',
    'Haiti': 'ht', 'Czechia': 'cz', 'New Zealand': 'nz', 'Curacao': 'cw'
  }

  /* ── Scroll-reveal via IntersectionObserver ──── */
  ngAfterViewInit() {
    // Trigger when block is at least 25% visible
    const observer = new IntersectionObserver(
      entries => {
        entries.forEach(entry => {
          if (entry.isIntersecting) {
            entry.target.classList.add('visible')
            // Once revealed, stop observing
            observer.unobserve(entry.target)
          }
        })
      },
      { threshold: 0.25 }
    )

    document.querySelectorAll('.block').forEach(el => observer.observe(el))
  }
}
