import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IApidiaexcepcional, NewApidiaexcepcional } from '../apidiaexcepcional.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IApidiaexcepcional for edit and NewApidiaexcepcionalFormGroupInput for create.
 */
type ApidiaexcepcionalFormGroupInput = IApidiaexcepcional | PartialWithRequiredKeyOf<NewApidiaexcepcional>;

type ApidiaexcepcionalFormDefaults = Pick<NewApidiaexcepcional, 'id'>;

type ApidiaexcepcionalFormGroupContent = {
  id: FormControl<IApidiaexcepcional['id'] | NewApidiaexcepcional['id']>;
  dadosdiaexcepcional: FormControl<IApidiaexcepcional['dadosdiaexcepcional']>;
  chave: FormControl<IApidiaexcepcional['chave']>;
};

export type ApidiaexcepcionalFormGroup = FormGroup<ApidiaexcepcionalFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ApidiaexcepcionalFormService {
  createApidiaexcepcionalFormGroup(apidiaexcepcional: ApidiaexcepcionalFormGroupInput = { id: null }): ApidiaexcepcionalFormGroup {
    const apidiaexcepcionalRawValue = {
      ...this.getFormDefaults(),
      ...apidiaexcepcional,
    };
    return new FormGroup<ApidiaexcepcionalFormGroupContent>({
      id: new FormControl(
        { value: apidiaexcepcionalRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      dadosdiaexcepcional: new FormControl(apidiaexcepcionalRawValue.dadosdiaexcepcional),
      chave: new FormControl(apidiaexcepcionalRawValue.chave),
    });
  }

  getApidiaexcepcional(form: ApidiaexcepcionalFormGroup): IApidiaexcepcional | NewApidiaexcepcional {
    return form.getRawValue() as IApidiaexcepcional | NewApidiaexcepcional;
  }

  resetForm(form: ApidiaexcepcionalFormGroup, apidiaexcepcional: ApidiaexcepcionalFormGroupInput): void {
    const apidiaexcepcionalRawValue = { ...this.getFormDefaults(), ...apidiaexcepcional };
    form.reset(
      {
        ...apidiaexcepcionalRawValue,
        id: { value: apidiaexcepcionalRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ApidiaexcepcionalFormDefaults {
    return {
      id: null,
    };
  }
}
