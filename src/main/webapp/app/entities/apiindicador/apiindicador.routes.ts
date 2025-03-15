import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import ApiindicadorResolve from './route/apiindicador-routing-resolve.service';

const apiindicadorRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/apiindicador.component').then(m => m.ApiindicadorComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/apiindicador-detail.component').then(m => m.ApiindicadorDetailComponent),
    resolve: {
      apiindicador: ApiindicadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/apiindicador-update.component').then(m => m.ApiindicadorUpdateComponent),
    resolve: {
      apiindicador: ApiindicadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/apiindicador-update.component').then(m => m.ApiindicadorUpdateComponent),
    resolve: {
      apiindicador: ApiindicadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default apiindicadorRoute;
