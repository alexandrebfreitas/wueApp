import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ApidiaexcepcionalService } from '../service/apidiaexcepcional.service';
import { IApidiaexcepcional } from '../apidiaexcepcional.model';
import { ApidiaexcepcionalFormService } from './apidiaexcepcional-form.service';

import { ApidiaexcepcionalUpdateComponent } from './apidiaexcepcional-update.component';

describe('Apidiaexcepcional Management Update Component', () => {
  let comp: ApidiaexcepcionalUpdateComponent;
  let fixture: ComponentFixture<ApidiaexcepcionalUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let apidiaexcepcionalFormService: ApidiaexcepcionalFormService;
  let apidiaexcepcionalService: ApidiaexcepcionalService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ApidiaexcepcionalUpdateComponent],
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
      .overrideTemplate(ApidiaexcepcionalUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ApidiaexcepcionalUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    apidiaexcepcionalFormService = TestBed.inject(ApidiaexcepcionalFormService);
    apidiaexcepcionalService = TestBed.inject(ApidiaexcepcionalService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const apidiaexcepcional: IApidiaexcepcional = { id: 19018 };

      activatedRoute.data = of({ apidiaexcepcional });
      comp.ngOnInit();

      expect(comp.apidiaexcepcional).toEqual(apidiaexcepcional);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApidiaexcepcional>>();
      const apidiaexcepcional = { id: 6594 };
      jest.spyOn(apidiaexcepcionalFormService, 'getApidiaexcepcional').mockReturnValue(apidiaexcepcional);
      jest.spyOn(apidiaexcepcionalService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apidiaexcepcional });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: apidiaexcepcional }));
      saveSubject.complete();

      // THEN
      expect(apidiaexcepcionalFormService.getApidiaexcepcional).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(apidiaexcepcionalService.update).toHaveBeenCalledWith(expect.objectContaining(apidiaexcepcional));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApidiaexcepcional>>();
      const apidiaexcepcional = { id: 6594 };
      jest.spyOn(apidiaexcepcionalFormService, 'getApidiaexcepcional').mockReturnValue({ id: null });
      jest.spyOn(apidiaexcepcionalService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apidiaexcepcional: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: apidiaexcepcional }));
      saveSubject.complete();

      // THEN
      expect(apidiaexcepcionalFormService.getApidiaexcepcional).toHaveBeenCalled();
      expect(apidiaexcepcionalService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApidiaexcepcional>>();
      const apidiaexcepcional = { id: 6594 };
      jest.spyOn(apidiaexcepcionalService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apidiaexcepcional });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(apidiaexcepcionalService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
