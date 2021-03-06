const path = require('path');
const fs = require('fs-extra');
const gulp = require('gulp');
const log = require('fancy-log');
const colors = require('ansi-colors');
const del = require('del');
const rollup = require('rollup');

const pkg = require('./package.json');
const rollupConfig = require('./rollup.config');

const ROOT = __dirname;
const NODE_MODULES = path.join(ROOT, 'node_modules');
const SRC = path.join(ROOT, 'src', 'main', 'resources', 'static');
const VENDORS = path.join(SRC, 'vendors');

gulp.task('clean', () => (
  del([VENDORS])
));

gulp.task('vendors', ['clean'], () => {
  fs.ensureDirSync(VENDORS);

  Object.keys(pkg.dependencies).forEach((id) => {
    const src = path.join(NODE_MODULES, id);
    const dest = path.join(VENDORS, id);

    log(colors.gray(`Copying ${id} from ${src} to ${dest}...`));
    fs.copySync(src, dest);
  });
});

gulp.task('build', ['vendors'], () => (
  rollup.rollup(rollupConfig).then((bundle) => (
      bundle.write(rollupConfig.output)
  ))
));

gulp.task('watch', ['build'], () => {
  const watcher = rollup.watch(rollupConfig);

  watcher.on('event', (event) => {
    log(colors.gray(`Rollup update: ${event.code}`));
  });
});
