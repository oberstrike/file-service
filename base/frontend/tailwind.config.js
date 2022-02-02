module.exports = {
  prefix: '',
  content: ['./public/index.html',
    './src/**/*.{html,js}'],
  darkMode: 'class', // or 'media' or 'class'
  theme: {
    extend: {},
  },
  variants: {
    extend: {},
  },
  plugins: [require('@tailwindcss/forms'), require('@tailwindcss/typography')],
};
