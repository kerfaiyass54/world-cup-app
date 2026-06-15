import { KeycloakOptions } from 'keycloak-angular';

export const keycloakConfig: KeycloakOptions = {
  config: {
    url: 'http://localhost:8080',
    realm: 'myrealm',
    clientId: 'angular-app'
  },
  initOptions: {
    onLoad: 'login-required',
    checkLoginIframe: false
  }
};
