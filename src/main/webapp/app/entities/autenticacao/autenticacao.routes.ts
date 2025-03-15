import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import AutenticacaoResolve from './route/autenticacao-routing-resolve.service';

const autenticacaoRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/autenticacao.component').then(m => m.AutenticacaoComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/autenticacao-detail.component').then(m => m.AutenticacaoDetailComponent),
    resolve: {
      autenticacao: AutenticacaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/autenticacao-update.component').then(m => m.AutenticacaoUpdateComponent),
    resolve: {
      autenticacao: AutenticacaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/autenticacao-update.component').then(m => m.AutenticacaoUpdateComponent),
    resolve: {
      autenticacao: AutenticacaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default autenticacaoRoute;
