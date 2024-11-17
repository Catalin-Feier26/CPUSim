import { render, screen, act } from '@testing-library/react';
import App from './App';

test('renders learn react link', () => {
  act(() => {
    render(<App />);
  });
  const linkElement = screen.getByText(/learn react/i);
  expect(linkElement).toBeInTheDocument();
});

test('renders MIPS diagram', () => {
  act(() => {
    render(<App />);
  });
  const mipsImage = screen.getByAltText(/MIPS Diagram/i);
  expect(mipsImage).toBeInTheDocument();
});