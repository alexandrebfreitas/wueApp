import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IAutenticacao, NewAutenticacao } from '../autenticacao.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAutenticacao for edit and NewAutenticacaoFormGroupInput for create.
 */
type AutenticacaoFormGroupInput = IAutenticacao | PartialWithRequiredKeyOf<NewAutenticacao>;

type AutenticacaoFormDefaults = Pick<NewAutenticacao, 'id'>;

type AutenticacaoFormGroupContent = {
  id: FormControl<IAutenticacao['id'] | NewAutenticacao['id']>;
  usuario: FormControl<IAutenticacao['usuario']>;
  senha: FormControl<IAutenticacao['senha']>;
  accessToken: FormControl<IAutenticacao['accessToken']>;
  tokenType: FormControl<IAutenticacao['tokenType']>;
};

export type AutenticacaoFormGroup = FormGroup<AutenticacaoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AutenticacaoFormService {
  createAutenticacaoFormGroup(autenticacao: AutenticacaoFormGroupInput = { id: null }): AutenticacaoFormGroup {
    const autenticacaoRawValue = {
      ...this.getFormDefaults(),
      ...autenticacao,
    };
    return new FormGroup<AutenticacaoFormGroupContent>({
      id: new FormControl(
        { value: autenticacaoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      usuario: new FormControl(autenticacaoRawValue.usuario),
      senha: new FormControl(autenticacaoRawValue.senha),
      accessToken: new FormControl(autenticacaoRawValue.accessToken),
      tokenType: new FormControl(autenticacaoRawValue.tokenType),
    });
  }

  getAutenticacao(form: AutenticacaoFormGroup): IAutenticacao | NewAutenticacao {
    return form.getRawValue() as IAutenticacao | NewAutenticacao;
  }

  resetForm(form: AutenticacaoFormGroup, autenticacao: AutenticacaoFormGroupInput): void {
    const autenticacaoRawValue = { ...this.getFormDefaults(), ...autenticacao };
    form.reset(
      {
        ...autenticacaoRawValue,
        id: { value: autenticacaoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AutenticacaoFormDefaults {
    return {
      id: null,
    };
  }
}
