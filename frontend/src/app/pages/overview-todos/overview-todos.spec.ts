import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OverviewTodos } from './overview-todos';

describe('OverviewTodos', () => {
  let component: OverviewTodos;
  let fixture: ComponentFixture<OverviewTodos>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OverviewTodos]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OverviewTodos);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
