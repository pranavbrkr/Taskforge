-- Add timestamps to tasks
alter table tasks
    add column created_at timestamptz not null default now(),
    add column updated_at timestamptz not null default now();

create index if not exists idx_tasks_created_at on tasks(created_at);