import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import ApiperiodoassistenciaResolve from './route/apiperiodoassistencia-routing-resolve.service';

const apiperiodoassistenciaRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/apiperiodoassistencia.component').then(m => m.ApiperiodoassistenciaComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/apiperiodoassistencia-detail.component').then(m => m.ApiperiodoassistenciaDetailComponent),
    resolve: {
      apiperiodoassistencia: ApiperiodoassistenciaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/apiperiodoassistencia-update.component').then(m => m.ApiperiodoassistenciaUpdateComponent),
    resolve: {
      apiperiodoassistencia: ApiperiodoassistenciaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/apiperiodoassistencia-update.component').then(m => m.ApiperiodoassistenciaUpdateComponent),
    resolve: {
      apiperiodoassistencia: ApiperiodoassistenciaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default apiperiodoassistenciaRoute;
