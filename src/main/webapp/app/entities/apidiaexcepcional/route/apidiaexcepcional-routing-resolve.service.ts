import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IApidiaexcepcional } from '../apidiaexcepcional.model';
import { ApidiaexcepcionalService } from '../service/apidiaexcepcional.service';

const apidiaexcepcionalResolve = (route: ActivatedRouteSnapshot): Observable<null | IApidiaexcepcional> => {
  const id = route.params.id;
  if (id) {
    return inject(ApidiaexcepcionalService)
      .find(id)
      .pipe(
        mergeMap((apidiaexcepcional: HttpResponse<IApidiaexcepcional>) => {
          if (apidiaexcepcional.body) {
            return of(apidiaexcepcional.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default apidiaexcepcionalResolve;
