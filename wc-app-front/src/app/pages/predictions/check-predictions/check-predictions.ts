import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PredictionService } from '../../../prediction.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-check-predictions',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './check-predictions.html',
  styleUrl: './check-predictions.css',
})
export class CheckPredictions implements OnInit {
  prediction: any = null;

  loading = true;

  constructor(private predictionService: PredictionService) {}

  ngOnInit(): void {
    const email = sessionStorage.getItem('email');

    if (!email) {
      this.loading = false;

      return;
    }

    this.predictionService.getPredictionByEmail(email).subscribe({
      next: (response: any) => {
        this.prediction = response.data.predictionByEmail;

        this.loading = false;
      },

      error: (error) => {
        console.error(error);

        this.loading = false;
      },
    });
  }
}
