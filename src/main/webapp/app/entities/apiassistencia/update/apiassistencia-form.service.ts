import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IApiassistencia, NewApiassistencia } from '../apiassistencia.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IApiassistencia for edit and NewApiassistenciaFormGroupInput for create.
 */
type ApiassistenciaFormGroupInput = IApiassistencia | PartialWithRequiredKeyOf<NewApiassistencia>;

type ApiassistenciaFormDefaults = Pick<NewApiassistencia, 'id'>;

type ApiassistenciaFormGroupContent = {
  id: FormControl<IApiassistencia['id'] | NewApiassistencia['id']>;
  filtro: FormControl<IApiassistencia['filtro']>;
  dadosassistencia: FormControl<IApiassistencia['dadosassistencia']>;
  chave: FormControl<IApiassistencia['chave']>;
};

export type ApiassistenciaFormGroup = FormGroup<ApiassistenciaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ApiassistenciaFormService {
  createApiassistenciaFormGroup(apiassistencia: ApiassistenciaFormGroupInput = { id: null }): ApiassistenciaFormGroup {
    const apiassistenciaRawValue = {
      ...this.getFormDefaults(),
      ...apiassistencia,
    };
    return new FormGroup<ApiassistenciaFormGroupContent>({
      id: new FormControl(
        { value: apiassistenciaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      filtro: new FormControl(apiassistenciaRawValue.filtro),
      dadosassistencia: new FormControl(apiassistenciaRawValue.dadosassistencia),
      chave: new FormControl(apiassistenciaRawValue.chave),
    });
  }

  getApiassistencia(form: ApiassistenciaFormGroup): IApiassistencia | NewApiassistencia {
    return form.getRawValue() as IApiassistencia | NewApiassistencia;
  }

  resetForm(form: ApiassistenciaFormGroup, apiassistencia: ApiassistenciaFormGroupInput): void {
    const apiassistenciaRawValue = { ...this.getFormDefaults(), ...apiassistencia };
    form.reset(
      {
        ...apiassistenciaRawValue,
        id: { value: apiassistenciaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ApiassistenciaFormDefaults {
    return {
      id: null,
    };
  }
}
