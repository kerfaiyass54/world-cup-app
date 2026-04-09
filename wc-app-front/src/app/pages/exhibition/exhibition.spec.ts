import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Exhibition } from './exhibition';

describe('Exhibition', () => {
  let component: Exhibition;
  let fixture: ComponentFixture<Exhibition>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Exhibition],
    }).compileComponents();

    fixture = TestBed.createComponent(Exhibition);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
