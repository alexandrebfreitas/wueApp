import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IAssistencias, NewAssistencias } from '../assistencias.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAssistencias for edit and NewAssistenciasFormGroupInput for create.
 */
type AssistenciasFormGroupInput = IAssistencias | PartialWithRequiredKeyOf<NewAssistencias>;

type AssistenciasFormDefaults = Pick<NewAssistencias, 'id'>;

type AssistenciasFormGroupContent = {
  id: FormControl<IAssistencias['id'] | NewAssistencias['id']>;
  value: FormControl<IAssistencias['value']>;
  apiassistencia: FormControl<IAssistencias['apiassistencia']>;
};

export type AssistenciasFormGroup = FormGroup<AssistenciasFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AssistenciasFormService {
  createAssistenciasFormGroup(assistencias: AssistenciasFormGroupInput = { id: null }): AssistenciasFormGroup {
    const assistenciasRawValue = {
      ...this.getFormDefaults(),
      ...assistencias,
    };
    return new FormGroup<AssistenciasFormGroupContent>({
      id: new FormControl(
        { value: assistenciasRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      value: new FormControl(assistenciasRawValue.value),
      apiassistencia: new FormControl(assistenciasRawValue.apiassistencia),
    });
  }

  getAssistencias(form: AssistenciasFormGroup): IAssistencias | NewAssistencias {
    return form.getRawValue() as IAssistencias | NewAssistencias;
  }

  resetForm(form: AssistenciasFormGroup, assistencias: AssistenciasFormGroupInput): void {
    const assistenciasRawValue = { ...this.getFormDefaults(), ...assistencias };
    form.reset(
      {
        ...assistenciasRawValue,
        id: { value: assistenciasRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AssistenciasFormDefaults {
    return {
      id: null,
    };
  }
}
