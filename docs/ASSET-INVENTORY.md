# Asset Inventory

## Launcher assets

The app includes launcher icon resources under:
- `mipmap-anydpi-v26/ic_launcher.xml`
- `mipmap-anydpi-v26/ic_launcher_round.xml`
- density-specific `mipmap-hdpi/`, `mipmap-mdpi/`, `mipmap-xhdpi/`, `mipmap-xxhdpi/`, and `mipmap-xxxhdpi/` WebP launcher assets.
- `drawable/ic_launcher_foreground.xml`
- `main/ic_launcher-playstore.png` application store artwork.

The manifest selects the standard and round mipmap launcher icons.

## Drawable illustrations and action icons

| Resource | Role |
|---|---|
| `drawable/folder_empty.xml` | Empty notes illustration |
| `drawable/sticky_note.xml` | Decorative note-list icon |
| `drawable/sticky_notes_add.xml` | New note floating action icon |
| `drawable/sticky_notes_del.xml` | Popup delete action icon |
| `drawable/sticky_notes_rename.xml` | Popup rename action icon |
| `drawable/sticky_notes_save.xml` | Editor save action icon |
| `drawable/sticky_notes_share.xml` | Share action icon |

These are vector drawable resources; they scale with density and theme tint attributes.

## Layout resources

| Resource | Used by |
|---|---|
| `layout/a_main.xml` | MainActivity |
| `layout/f_main.xml` | FMain |
| `layout/f_edit.xml` | FEditor |
| `layout/i_note.xml` | NotesAdapter row |
| `layout/d_rename.xml` | Create-title and rename dialogs |

## Screenshots

The `screenshots/` directory contains six reference images:
- `ss1.png`
- `ss2.png`
- `ss3.png`
- `ss4.png`
- `ss5.png`
- `ss6.png`

Screenshots are repository documentation artifacts and are not runtime resources.

## Values and themes

- `values/strings.xml`: user-visible labels, hints, messages and new error strings.
- `values/colors.xml`: base black, white and launcher background.
- `values/themes.xml`: light Material 3 theme.
- `values-night/themes.xml`: dark Material 3 theme.
- `values-v27/themes.xml`: light system-bar variant.
- `values-night-v27/themes.xml`: dark system-bar variant.

## Asset maintenance

- Keep launcher density variants aligned when changing the app icon.
- Use vector drawables for simple scalable UI glyphs.
- Keep decorative images inaccessible to screen readers when they convey no information.
- Provide a content description for controls, not decorative artwork.
- Review asset ownership and distribution rights before publishing.
