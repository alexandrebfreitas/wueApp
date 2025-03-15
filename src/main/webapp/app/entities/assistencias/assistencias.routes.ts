import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import AssistenciasResolve from './route/assistencias-routing-resolve.service';

const assistenciasRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/assistencias.component').then(m => m.AssistenciasComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/assistencias-detail.component').then(m => m.AssistenciasDetailComponent),
    resolve: {
      assistencias: AssistenciasResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/assistencias-update.component').then(m => m.AssistenciasUpdateComponent),
    resolve: {
      assistencias: AssistenciasResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/assistencias-update.component').then(m => m.AssistenciasUpdateComponent),
    resolve: {
      assistencias: AssistenciasResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default assistenciasRoute;
