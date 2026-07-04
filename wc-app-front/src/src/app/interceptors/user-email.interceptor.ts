import { HttpInterceptorFn } from '@angular/common/http';
import { keycloak } from '../../../app/keycloak.config';

export const userEmailInterceptor: HttpInterceptorFn = (req, next) => {

  const email = keycloak.tokenParsed?.['email'];

  if (!email) {
    return next(req);
  }

  return next(
    req.clone({
      setHeaders: {
        'X-User-Email': email
      }
    })
  );
};
