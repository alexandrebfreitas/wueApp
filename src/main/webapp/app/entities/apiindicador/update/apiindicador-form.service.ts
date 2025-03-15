import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IApiindicador, NewApiindicador } from '../apiindicador.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IApiindicador for edit and NewApiindicadorFormGroupInput for create.
 */
type ApiindicadorFormGroupInput = IApiindicador | PartialWithRequiredKeyOf<NewApiindicador>;

type ApiindicadorFormDefaults = Pick<NewApiindicador, 'id'>;

type ApiindicadorFormGroupContent = {
  id: FormControl<IApiindicador['id'] | NewApiindicador['id']>;
  filtro: FormControl<IApiindicador['filtro']>;
  dadosindicador: FormControl<IApiindicador['dadosindicador']>;
  chave: FormControl<IApiindicador['chave']>;
};

export type ApiindicadorFormGroup = FormGroup<ApiindicadorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ApiindicadorFormService {
  createApiindicadorFormGroup(apiindicador: ApiindicadorFormGroupInput = { id: null }): ApiindicadorFormGroup {
    const apiindicadorRawValue = {
      ...this.getFormDefaults(),
      ...apiindicador,
    };
    return new FormGroup<ApiindicadorFormGroupContent>({
      id: new FormControl(
        { value: apiindicadorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      filtro: new FormControl(apiindicadorRawValue.filtro),
      dadosindicador: new FormControl(apiindicadorRawValue.dadosindicador),
      chave: new FormControl(apiindicadorRawValue.chave),
    });
  }

  getApiindicador(form: ApiindicadorFormGroup): IApiindicador | NewApiindicador {
    return form.getRawValue() as IApiindicador | NewApiindicador;
  }

  resetForm(form: ApiindicadorFormGroup, apiindicador: ApiindicadorFormGroupInput): void {
    const apiindicadorRawValue = { ...this.getFormDefaults(), ...apiindicador };
    form.reset(
      {
        ...apiindicadorRawValue,
        id: { value: apiindicadorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ApiindicadorFormDefaults {
    return {
      id: null,
    };
  }
}
