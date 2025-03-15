import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import ApiassistenciaResolve from './route/apiassistencia-routing-resolve.service';

const apiassistenciaRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/apiassistencia.component').then(m => m.ApiassistenciaComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/apiassistencia-detail.component').then(m => m.ApiassistenciaDetailComponent),
    resolve: {
      apiassistencia: ApiassistenciaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/apiassistencia-update.component').then(m => m.ApiassistenciaUpdateComponent),
    resolve: {
      apiassistencia: ApiassistenciaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/apiassistencia-update.component').then(m => m.ApiassistenciaUpdateComponent),
    resolve: {
      apiassistencia: ApiassistenciaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default apiassistenciaRoute;
