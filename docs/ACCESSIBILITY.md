# Accessibility Guidance

This document describes current implementation choices and practical checks. It is not a certification of WCAG or Android accessibility conformance.

## Current interface semantics

- Main screen uses a RecyclerView for the note list.
- Each row is a MaterialCardView containing the note title and a decorative icon.
- Empty state uses a decorative illustration and a text message.
- New note action is a FloatingActionButton with a descriptive content description.
- Editor uses a multiline EditText with a visible hint.
- Rename dialog uses a Material TextInputLayout hint and TextInputEditText.
- Toolbar menu items use string resources for Save, Share, Rename and Delete.

## Screen reader behaviour

Decorative icons are marked as not important for accessibility. The new-note floating action button has the content description "Create a new note". The note title is rendered as text rather than embedded in an image.

## Keyboard and focus

Test the editor using a physical keyboard and Android accessibility services:
- focus can enter the note editor;
- the toolbar Save and Share actions are reachable;
- the Save As title field is announced with its hint;
- validation errors are announced or visible;
- back navigation does not silently lose an in-progress change without the expected user action.

## Text and display

Verify large font settings, display size changes, landscape orientation, split screen and small screen dimensions. Long note titles should not hide all available controls. Long note bodies should scroll without making the toolbar unreachable.

## Theme support

The project provides Material 3 light and dark theme resource variants. Test both with high-contrast system settings where available. The current project does not define a full custom contrast audit.

## Recommended manual audit

- [ ] TalkBack can identify the add-note action.
- [ ] TalkBack can read each note title.
- [ ] Long-press menu actions have meaningful labels.
- [ ] Save and Share actions are announced by title.
- [ ] The note editor has a clear accessible hint/label.
- [ ] Rename dialog field has an accessible label.
- [ ] Validation and save/load errors are perceivable.
- [ ] Large text does not clip primary actions.
- [ ] Light/dark themes maintain readable contrast.
- [ ] Touch targets meet Android design guidance.
- [ ] The app remains usable in portrait and landscape.
