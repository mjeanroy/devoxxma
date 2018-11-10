const path = require('path');
const nodeResolve = require('rollup-plugin-node-resolve');
const commonjs = require('rollup-plugin-commonjs');
const babel = require('rollup-plugin-babel');

module.exports = {
  input: path.join(__dirname, 'src', 'main', 'resources', 'static', 'js', 'app.js'),

  output: {
    file: path.join(__dirname, 'src', 'main', 'resources', 'static', 'bundle.js'),
    name: 'DevoxxMA',
    format: 'iife'
  },

  plugins: [
    nodeResolve(),
    commonjs(),
    babel(),
  ],
};