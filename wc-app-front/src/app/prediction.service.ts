import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PredictionService {

  private http = inject(HttpClient);

  private api = 'http://localhost:8012/graphql';

  savePrediction(request: any) {

    return this.http.post(this.api, {
      query: `
        mutation SavePrediction(
          $request: PredictionRequest!
        ) {
          savePrediction(
            request: $request
          ) {
            id
            email
            champion
          }
        }
      `,
      variables: {
        request
      }
    });
  }

  getPredictions() {

    return this.http.post(this.api, {
      query: `
        query {
          predictions {
            id
            email
            champion
          }
        }
      `
    });
  }

  getPredictionByEmail(email: string) {

    return this.http.post(this.api, {
      query: `
        query($email: String!) {
          predictionByEmail(
            email: $email
          ) {
            id
            email
            champion
          }
        }
      `,
      variables: {
        email
      }
    });
  }
}
