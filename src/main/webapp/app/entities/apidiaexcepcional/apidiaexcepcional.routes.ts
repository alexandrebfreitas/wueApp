import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import ApidiaexcepcionalResolve from './route/apidiaexcepcional-routing-resolve.service';

const apidiaexcepcionalRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/apidiaexcepcional.component').then(m => m.ApidiaexcepcionalComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/apidiaexcepcional-detail.component').then(m => m.ApidiaexcepcionalDetailComponent),
    resolve: {
      apidiaexcepcional: ApidiaexcepcionalResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/apidiaexcepcional-update.component').then(m => m.ApidiaexcepcionalUpdateComponent),
    resolve: {
      apidiaexcepcional: ApidiaexcepcionalResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/apidiaexcepcional-update.component').then(m => m.ApidiaexcepcionalUpdateComponent),
    resolve: {
      apidiaexcepcional: ApidiaexcepcionalResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default apidiaexcepcionalRoute;
