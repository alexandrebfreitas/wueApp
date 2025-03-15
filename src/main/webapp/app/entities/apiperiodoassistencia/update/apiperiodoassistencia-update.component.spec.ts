import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ApiperiodoassistenciaService } from '../service/apiperiodoassistencia.service';
import { IApiperiodoassistencia } from '../apiperiodoassistencia.model';
import { ApiperiodoassistenciaFormService } from './apiperiodoassistencia-form.service';

import { ApiperiodoassistenciaUpdateComponent } from './apiperiodoassistencia-update.component';

describe('Apiperiodoassistencia Management Update Component', () => {
  let comp: ApiperiodoassistenciaUpdateComponent;
  let fixture: ComponentFixture<ApiperiodoassistenciaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let apiperiodoassistenciaFormService: ApiperiodoassistenciaFormService;
  let apiperiodoassistenciaService: ApiperiodoassistenciaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ApiperiodoassistenciaUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ApiperiodoassistenciaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ApiperiodoassistenciaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    apiperiodoassistenciaFormService = TestBed.inject(ApiperiodoassistenciaFormService);
    apiperiodoassistenciaService = TestBed.inject(ApiperiodoassistenciaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const apiperiodoassistencia: IApiperiodoassistencia = { id: 5285 };

      activatedRoute.data = of({ apiperiodoassistencia });
      comp.ngOnInit();

      expect(comp.apiperiodoassistencia).toEqual(apiperiodoassistencia);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApiperiodoassistencia>>();
      const apiperiodoassistencia = { id: 24259 };
      jest.spyOn(apiperiodoassistenciaFormService, 'getApiperiodoassistencia').mockReturnValue(apiperiodoassistencia);
      jest.spyOn(apiperiodoassistenciaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apiperiodoassistencia });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: apiperiodoassistencia }));
      saveSubject.complete();

      // THEN
      expect(apiperiodoassistenciaFormService.getApiperiodoassistencia).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(apiperiodoassistenciaService.update).toHaveBeenCalledWith(expect.objectContaining(apiperiodoassistencia));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApiperiodoassistencia>>();
      const apiperiodoassistencia = { id: 24259 };
      jest.spyOn(apiperiodoassistenciaFormService, 'getApiperiodoassistencia').mockReturnValue({ id: null });
      jest.spyOn(apiperiodoassistenciaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apiperiodoassistencia: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: apiperiodoassistencia }));
      saveSubject.complete();

      // THEN
      expect(apiperiodoassistenciaFormService.getApiperiodoassistencia).toHaveBeenCalled();
      expect(apiperiodoassistenciaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApiperiodoassistencia>>();
      const apiperiodoassistencia = { id: 24259 };
      jest.spyOn(apiperiodoassistenciaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apiperiodoassistencia });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(apiperiodoassistenciaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
