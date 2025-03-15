import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'wattsUpEnergyApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'apiassistencia',
    data: { pageTitle: 'wattsUpEnergyApp.apiassistencia.home.title' },
    loadChildren: () => import('./apiassistencia/apiassistencia.routes'),
  },
  {
    path: 'apidiaexcepcional',
    data: { pageTitle: 'wattsUpEnergyApp.apidiaexcepcional.home.title' },
    loadChildren: () => import('./apidiaexcepcional/apidiaexcepcional.routes'),
  },
  {
    path: 'apiindicador',
    data: { pageTitle: 'wattsUpEnergyApp.apiindicador.home.title' },
    loadChildren: () => import('./apiindicador/apiindicador.routes'),
  },
  {
    path: 'apiperiodoassistencia',
    data: { pageTitle: 'wattsUpEnergyApp.apiperiodoassistencia.home.title' },
    loadChildren: () => import('./apiperiodoassistencia/apiperiodoassistencia.routes'),
  },
  {
    path: 'assistencias',
    data: { pageTitle: 'wattsUpEnergyApp.assistencias.home.title' },
    loadChildren: () => import('./assistencias/assistencias.routes'),
  },
  {
    path: 'autenticacao',
    data: { pageTitle: 'wattsUpEnergyApp.autenticacao.home.title' },
    loadChildren: () => import('./autenticacao/autenticacao.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
