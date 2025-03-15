import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IApiperiodoassistencia, NewApiperiodoassistencia } from '../apiperiodoassistencia.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IApiperiodoassistencia for edit and NewApiperiodoassistenciaFormGroupInput for create.
 */
type ApiperiodoassistenciaFormGroupInput = IApiperiodoassistencia | PartialWithRequiredKeyOf<NewApiperiodoassistencia>;

type ApiperiodoassistenciaFormDefaults = Pick<NewApiperiodoassistencia, 'id'>;

type ApiperiodoassistenciaFormGroupContent = {
  id: FormControl<IApiperiodoassistencia['id'] | NewApiperiodoassistencia['id']>;
  dadosperiodo: FormControl<IApiperiodoassistencia['dadosperiodo']>;
  chave: FormControl<IApiperiodoassistencia['chave']>;
};

export type ApiperiodoassistenciaFormGroup = FormGroup<ApiperiodoassistenciaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ApiperiodoassistenciaFormService {
  createApiperiodoassistenciaFormGroup(
    apiperiodoassistencia: ApiperiodoassistenciaFormGroupInput = { id: null },
  ): ApiperiodoassistenciaFormGroup {
    const apiperiodoassistenciaRawValue = {
      ...this.getFormDefaults(),
      ...apiperiodoassistencia,
    };
    return new FormGroup<ApiperiodoassistenciaFormGroupContent>({
      id: new FormControl(
        { value: apiperiodoassistenciaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      dadosperiodo: new FormControl(apiperiodoassistenciaRawValue.dadosperiodo),
      chave: new FormControl(apiperiodoassistenciaRawValue.chave),
    });
  }

  getApiperiodoassistencia(form: ApiperiodoassistenciaFormGroup): IApiperiodoassistencia | NewApiperiodoassistencia {
    return form.getRawValue() as IApiperiodoassistencia | NewApiperiodoassistencia;
  }

  resetForm(form: ApiperiodoassistenciaFormGroup, apiperiodoassistencia: ApiperiodoassistenciaFormGroupInput): void {
    const apiperiodoassistenciaRawValue = { ...this.getFormDefaults(), ...apiperiodoassistencia };
    form.reset(
      {
        ...apiperiodoassistenciaRawValue,
        id: { value: apiperiodoassistenciaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ApiperiodoassistenciaFormDefaults {
    return {
      id: null,
    };
  }
}
