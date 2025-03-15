import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAutenticacao } from '../autenticacao.model';
import { AutenticacaoService } from '../service/autenticacao.service';

const autenticacaoResolve = (route: ActivatedRouteSnapshot): Observable<null | IAutenticacao> => {
  const id = route.params.id;
  if (id) {
    return inject(AutenticacaoService)
      .find(id)
      .pipe(
        mergeMap((autenticacao: HttpResponse<IAutenticacao>) => {
          if (autenticacao.body) {
            return of(autenticacao.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default autenticacaoResolve;
