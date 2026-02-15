-- Projects
create table if not exists projects (
    id varchar(255) primary key,
    name varchar(100) not null,
    key varchar(10) not null unique
);

-- Tasks
create table if not exists tasks (
    id varchar(255) primary key,
    title varchar(200) not null,
    status varchar(20) not null,
    project_id varchar(255) not null,
    constraint fk_tasks_project foreign key (project_id) references projects(id)
);

create index if not exists idx_tasks_project_id on tasks(project_id);

-- Audit logs
create table if not exists audit_logs (
    id varchar(255) primary key,
    event_type varchar(50) not null,
    message varchar(255) not null,
    created_at timestamptz not null
);

create index if not exists idx_audit_created_at on audit_logs(created_at);